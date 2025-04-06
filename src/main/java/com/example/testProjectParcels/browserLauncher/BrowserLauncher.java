package com.example.testProjectParcels.browserLauncher;

import com.example.testProjectParcels.enums.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class BrowserLauncher implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(BrowserLauncher.class);

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Override
    public void run(ApplicationArguments args) {
        String os = System.getProperty("os.name").toLowerCase();

        try {
            ProcessBuilder processBuilder;

            if (os.contains("win")) {
                processBuilder = new ProcessBuilder("cmd", "/c", "start", frontendUrl);
            } else if (os.contains("mac")) {
                processBuilder = new ProcessBuilder("open", frontendUrl);
            } else if (os.contains("nix") || os.contains("nux")) {
                processBuilder = new ProcessBuilder("xdg-open", frontendUrl);
            } else {
                logger.warn("{}{}", Messages.UNSUPPORTED_BROWSER.get(), frontendUrl);
                return;
            }

            processBuilder.start();
            logger.info("{}{}", Messages.OPENING_BROWSER.get(), frontendUrl);

        } catch (Exception e) {
            logger.error(Messages.FAILED_TO_OPEN_BROWSER.get(), e.getMessage());
        }
    }
}
