package com.likelion.hackathon.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "userId")
    private String userId;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "icecream")
    private int icecream;

    @Column(name = "agree")
    private int agree;

    @Column(name = "image")
    private String image;

    @Column(name = "role")
    private String role;

    // @OneToMany(cascade = CascadeType.ALL)
    // @JoinTable(name = "habit")
    // @JoinColumn(name = "userId")
    // private List<Habit> habits;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new SimpleGrantedAuthority(role));
        return collection;
    }

    @Override
    public String getUsername() {
        return userId;
    }
}
