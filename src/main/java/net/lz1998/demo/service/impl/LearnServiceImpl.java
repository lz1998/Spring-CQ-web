package net.lz1998.demo.service.impl;

import net.lz1998.demo.entity.Learn;
import net.lz1998.demo.repository.LearnRepository;
import net.lz1998.demo.service.LearnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class LearnServiceImpl implements LearnService {
    @Autowired
    private LearnRepository learnRepository;

    /**
     * 获取回复内容
     *
     * @param groupId 群号
     * @param ask 问内容
     * @return 回复内容
     */
    @Override
    public String getAnswer(Long groupId, String ask) {
        Learn learn = learnRepository.findFirstByGroupIdAndAsk(groupId, ask);
        if (learn == null) {
            return "";
        }
        return learn.getAnswer();
    }

    @Override
    public void setAnswer(Long groupId, String ask, String answer, Long adminId) {
        Learn learn = Learn.builder()
                .groupId(groupId)
                .ask(ask)
                .answer(answer)
                .adminId(adminId)
                .gmtModified(new Date())
                .build();
        learnRepository.save(learn);
    }

    @Override
    public void clear(Long groupId) {
        learnRepository.deleteLearnsByGroupId(groupId);
    }

    @Override
    public Page<Learn> getGroupLearnPage(Long groupId, Pageable pageable) {
        return learnRepository.findLearnsByGroupId(groupId, pageable);
    }

    @Override
    public void deleteGroupLearn(Long groupId, String ask) {
        learnRepository.deleteLearnByGroupIdAndAsk(groupId, ask);
    }
}
