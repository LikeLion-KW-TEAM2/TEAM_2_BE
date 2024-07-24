package com.likelion.hackathon.domain;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "name")
    private String name;

    // 모두 보기 : 0, 나만 보기 : 1
    @Column(name = "privacy")
    private int privacy;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDate createdAt;

    // 매일 : 0, 주중 : 1, 주말 : 2
    @Column(name = "period_type")
    private int periodType;

    // 아직 극복 못 함 : 0, 극복 : 1
    @Column(name = "overcome")
    private int overcome;

    // @OneToMany(cascade = CascadeType.ALL)
    // @JoinTable(name = "history")
    // @JoinColumn(name = "habitId")
    // private List<History> historys;
}
