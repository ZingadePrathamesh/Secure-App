package com.secureapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class BruteForceProtectionService {
    private static final long LOCK_TIME = 15 * 60 * 1000;
    private static final int MAX_ATTEMPT = 5;

    private final ConcurrentHashMap<String, Long> lockChache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> attemptsCache = new ConcurrentHashMap<>();

    public void loginSucceed(String key){
        System.out.println("Login succeed method");
        lockChache.remove(key);
        attemptsCache.remove(key);
    }

    public void loginFailed(String key){
        System.out.println("Login failed method");
        int attempts = attemptsCache.compute(key, (k, v) -> (v == null) ? 1 : v + 1);

        System.out.println("Login failed : " + attempts);
        if(attempts >= MAX_ATTEMPT){
            System.out.println("account should be blocked now");
            lockChache.putIfAbsent(key, System.currentTimeMillis());
        }
    }

    public boolean isBlocked(String key){
        if(!lockChache.containsKey(key)){
            System.out.println("not blocked yet");
            return false;
        }
        long lockTime = lockChache.get(key);
        if(System.currentTimeMillis() - lockTime >= LOCK_TIME){
            lockChache.remove(key);
            return false;
        }
        return true;
    }
}
