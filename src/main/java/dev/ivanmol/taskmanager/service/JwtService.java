package dev.ivanmol.taskmanager.service;

import dev.ivanmol.taskmanager.model.user.User;
import io.jsonwebtoken.Claims;

public interface JwtService {
    String generateToken(User user);

    Claims parseClaims(String token);
}
