//package com.mdm.equipmentservice.model.mongo;
//
//import com.mdm.equipmentservice.model.entity.Notification;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.data.querydsl.QuerydslPredicateExecutor;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface NotificationMongoRepository extends MongoRepository<Notification, Long>, QuerydslPredicateExecutor<Notification> {
//    public Page<Notification> findAll(Pageable pageable);
//    public void deleteById(String id);
//    public void deleteAllById(List<String> ids);
////    public Page<Notification> findByNotificationTypeAndEquipmentId(NotificationType type, Long equipmentId, Pageable pageable);
//}
// commit by pvh