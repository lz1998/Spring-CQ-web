package net.lz1998.demo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.lz1998.demo.entity.Learn;
import org.springframework.data.domain.Page;

@AllArgsConstructor
@Data
public class GroupLearnPageResult {
    private Page<Learn> page;
    private String groupName;
    private boolean canUpdate;
}
