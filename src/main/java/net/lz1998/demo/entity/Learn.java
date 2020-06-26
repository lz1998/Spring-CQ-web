package net.lz1998.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "bot_learn")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(LearnKey.class) // groupId,ask联合主键
public class Learn implements Serializable {
    @Id
    @Column
    private Long groupId;
    @Id
    @Column(length = 180, nullable = false)
    private String ask;
    @Column(length = 2000, nullable = false)
    private String answer;
    @Column(nullable = false)
    private Long adminId;// 设置者ID
    @UpdateTimestamp
    @Column(nullable = false)
    private Date gmtModified;
}
