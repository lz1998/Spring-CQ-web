package net.lz1998.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "bot_welcome")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Welcome {
    @Id
    private Long groupId;

    @Column(length = 2000)
    private String welcomeMsg;

    @Column(nullable = false)
    private Long adminId;

    @UpdateTimestamp
    @Column(nullable = false)
    private Date gmtModified;
}
