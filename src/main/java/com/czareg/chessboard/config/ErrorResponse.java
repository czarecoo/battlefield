package com.czareg.chessboard.config;

import java.time.Instant;
import java.util.List;

public record ErrorResponse(Instant timestamp, int status, String reason, String path, List<String> errors, String message, String uuid) {
}