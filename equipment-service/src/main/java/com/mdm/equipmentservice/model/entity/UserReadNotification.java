//package com.mdm.equipmentservice.model.entity;
//
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@Getter
//@Setter
//@RequiredArgsConstructor
//@Table(name = "user_read_notifications")
//public class UserReadNotification {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", nullable = false)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn
//    private User user;
//
////    @ManyToOne
////    @JoinColumn
////    private Notification notification;
//    @Column(nullable = false)
//    private boolean isRead;
//}
