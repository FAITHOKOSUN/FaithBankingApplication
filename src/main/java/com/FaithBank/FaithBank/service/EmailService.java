package com.FaithBank.FaithBank.service;

import com.FaithBank.FaithBank.dto.EmailDetails;

public interface EmailService {
    void sendEmailAlert(EmailDetails emailDetails);
    void sendEmailAlertWithAttachment(EmailDetails emailDetails);
}
