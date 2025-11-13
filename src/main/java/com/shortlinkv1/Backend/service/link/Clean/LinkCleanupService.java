package com.shortlinkv1.Backend.service.link.Clean;

import com.shortlinkv1.Backend.repository.ShortLink.LinkRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
public class LinkCleanupService {

    @Autowired
    private LinkRepository linkRepository;

    @Scheduled(fixedRate = 3600000)
    public void deleteExpiredLinksService(){
        linkRepository.deleteByExpiresAtBefore(LocalDateTime.now());
    }

}
