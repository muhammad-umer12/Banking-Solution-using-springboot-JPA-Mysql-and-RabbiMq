package com.tum.Banking.model.Account;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.NumberFormat;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "account")

public class CustomerAccount {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

//    @Id
//    @GeneratedValue
//    private UUID id;

    @Column(name = "customer_id")
    private int customerId;

    @Column(name = "country")
    private String country;

    @OneToMany(mappedBy = "account_id")
    private List<AccountBalance> balance;

}
