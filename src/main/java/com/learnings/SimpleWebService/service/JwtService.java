package com.learnings.SimpleWebService.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private String key="";

    public JwtService(){
        try {
            //Yes, you did create an object — but indirectly.
            // The getInstance() method is a static factory that returns a KeyGenerator object for you,
            // instead of you calling new yourself.
            //Imagine going to a factory and saying: “Give me a machine that makes keys for HMAC‑SHA256.”
            //The factory hands you back a KeyGenerator object.
            KeyGenerator keygen=KeyGenerator.getInstance("HmacSHA256");

            //keygen.generateKey()
            //You press the button on that machine, and it spits out a brand‑new random secret key.
            SecretKey sk= keygen.generateKey();

            //Keys are just raw bytes (like unreadable binary).
            //Base64 turns those bytes into a neat text string so we can store it easily.
            key= Base64.getEncoder().encodeToString(sk.getEncoded());

            //Now your key field holds a secure random secret key in text form.

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    public String generateToken(String username) {

        // A bag where you can put extra info about the user.
        //Example: role, email, department.
        //These are called claims.
        Map<String,Object> hm=new HashMap<>();

        return Jwts.builder()
                .claims()
                .add(hm)        //Put in the bag of claims
                .subject(username) //standard claim: subject (user ID)
                .issuedAt(new Date(System.currentTimeMillis())) //when token was created
                .expiration(new Date(System.currentTimeMillis()+1000*60*30)) //when token will expire 30MIN
                .and()
                .signWith(getKey()) //sign with secret key
                .compact(); //build final token string

//        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30";
    }

    private SecretKey getKey() {
        //Remember we stored the key as text? Now we turn it back into raw bytes.
        byte[] keyBytes= Decoders.BASE64.decode(key);//converts string to bytes

        //Wraps those bytes into a Key object that JJWT can use to sign tokens.
        //Think of it like unlocking your password from the sticky note so you can actually use it.
        return Keys.hmacShaKeyFor(keyBytes);//hmacShaKeyFor accepts only bytes
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUserName(String token) {
        // extract the username from jwt token
        //what is Claims::getSubject,It means: “Call the getSubject() method on a Claims object.”
        //short and for claims -> claims.getSubject()
        return extractClaim(token, Claims::getSubject);
    }

    //helper method
    //The <T> means this method can return any type (String, Date, Boolean, etc.).
    //Function<Claims, T> claimResolver
    //This is a function you pass in that knows which claim you want.
    //Example:
    //Claims::getSubject → gets the username (sub claim).
    //Claims::getExpiration → gets the expiration date (exp claim).
    //Without Function<Claims, T>you’d need a separate helper for each document type
    // (one for name, one for expiry, one for role).
    //Function will take Cliams type and can return any type i.e.,T and claimResolver is what claim you need
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        //claimResolver.apply(claims)
        //Applies the function to the claims object.
        //Basically: “From all the claims, pick out the one I asked for.”
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {

        //You grab a letter opener (Jwts.parser()).
        //You check the seal with the official stamp (verifyWith(getKey())).
        //You prepare the opener (build()).
        //You open the envelope (parseSignedClaims(token)).
        //You read the letter inside (getPayload()).
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }



    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
