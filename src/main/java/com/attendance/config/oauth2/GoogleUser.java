package com.attendance.config.oauth2;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by developer on 11/1/2561.
 */

//ignore warning serial
@SuppressWarnings("serial")
public class GoogleUser implements UserDetails {

    private final String id;
    private final String email;
    private final boolean emailVerified;
    private final String userName;
    private final String givenName;
    private final String familyName;
    private final URI picture;
    private final Locale locale;
    private final String hd;

    public GoogleUser(
            String id,  String userName, String givenName, String familyName, URI picture,String email, Boolean emailVerified, Locale locale,String hd) {
        super();
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.givenName = givenName;
        this.familyName = familyName;
        this.picture = picture;
        this.locale = locale;
        this.emailVerified = emailVerified;
        this.hd = hd;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public URI getPicture() {
        return picture;
    }

    public Locale getLocale() {
        return locale;
    }

    public boolean getEmailVerified() {
        return emailVerified;
    }

    public String getHd(){
        return hd;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("GoogleUser [id=");
        builder.append(id);
        builder.append(", email=");
        builder.append(email);
        builder.append(", userName=");
        builder.append(userName);
        builder.append("]");
        return builder.toString();
    }

    public static GoogleUser fromUserInfoMap(Map<String, Object> map) {
        URI link;
        if (map.containsKey("link")) {
            link = getUri(map, "link");
        } else {
            link = getUri(map, "profile");
        }
        URI picture = getUri(map, "picture");
        URI profile = getUri(map, "profile");
        return new GoogleUser(
                (String) (map.containsKey("id") ? map.get("id") : map.get("sub")),
                (String) map.get("name"),
                (String) map.get("given_name"),
                (String) map.get("family_name"),
                picture,
                (String) map.get("email"),
                (boolean) map.get("email_verified"),
                new Locale((String) map.get("locale")),
                (String) map.get("hd"));
    }

    private static URI getUri(Map<String, Object> map, String key) {
        URI uri = null;
        try {
            if (map.containsKey(key)) {
                uri = new URI((String) map.get(key));
            }
        } catch (URISyntaxException e) {}
        return uri;
    }

}