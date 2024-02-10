package com.cloudbees.productmanagement.utils;

import jakarta.persistence.Id;

public class IdGenerator {
    public static String generate(){
            return String.format("%s%s","PD",System.currentTimeMillis());
    }
}
