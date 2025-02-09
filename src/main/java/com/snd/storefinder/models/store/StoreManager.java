package com.snd.storefinder.models.store;

import com.snd.storefinder.enums.UserRole;
import com.snd.storefinder.models.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "storemanager")
public class StoreManager {
    @EmbeddedId
    private StoreManagerId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @MapsId("storeId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Size(max = 8)
    @Column(name = "role", length = 8)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

}