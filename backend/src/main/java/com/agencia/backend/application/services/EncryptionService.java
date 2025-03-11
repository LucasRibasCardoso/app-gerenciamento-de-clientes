package com.agencia.backend.application.services;

public interface EncryptionService {
  String encrypt(String rawText);
  String decrypt(String encryptedText);
  String hash(String rawText);
}
