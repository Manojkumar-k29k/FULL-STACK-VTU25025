package com.jobportal.job_portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class JobPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobPortalApplication.class, args);
	}

	@Component
	static class StartupLogger {
		@EventListener(ApplicationReadyEvent.class)
		public void onReady() {
			System.out.println("\n" +
				"╔══════════════════════════════════════════════╗\n" +
				"║       ✅ Job Portal Started Successfully!     ║\n" +
				"║                                              ║\n" +
				"║   🌐 URL : http://localhost:8080             ║\n" +
				"║   🔐 Login: http://localhost:8080/login      ║\n" +
				"║   📝 Register: http://localhost:8080/register║\n" +
				"╚══════════════════════════════════════════════╝\n");
		}
	}

}
