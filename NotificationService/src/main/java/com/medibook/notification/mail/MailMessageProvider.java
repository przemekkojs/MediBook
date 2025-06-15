package com.medibook.notification.mail;

public class MailMessageProvider {
    private static String CLIENT_VISIT_CONFIRMATION_BODY = " "+
            "<html>" +
            "  <body style=\"font-family: Arial, sans-serif; color: #333;\">" +
            "    <h2>Appointment Confirmation</h2>" +
            "    <p>Dear <strong>{{client_name}}</strong>,</p>" +
            "    <p>Your visit has been successfully scheduled.</p>" +
            "    <table style=\"border-collapse: collapse;\">" +
            "      <tr>" +
            "        <td style=\"padding: 8px; font-weight: bold;\">Date:</td>" +
            "        <td style=\"padding: 8px;\">{{appointment_date}}</td>" +
            "      </tr>" +
            "      <tr>" +
            "        <td style=\"padding: 8px; font-weight: bold;\">Starting Hour:</td>" +
            "        <td style=\"padding: 8px;\">{{starting_hour}}</td>" +
            "      </tr>" +
            "      <tr>" +
            "        <td style=\"padding: 8px; font-weight: bold;\">Doctor:</td>" +
            "        <td style=\"padding: 8px;\">Dr. {{doctor_name}}</td>" +
            "      </tr>" +
            "    </table>" +
            "    <p>If you have any questions or need to make changes, feel free to contact us.</p>" +
            "    <p>Thank you!</p>" +
            "    <p><em>Your Medical Team</em></p>" +
            "  </body>" +
            "</html>";

    public static String clientVisitConfirmation(String clientName, String appointmentDate, String startingHour, String doctorName) {
        return CLIENT_VISIT_CONFIRMATION_BODY
                .replace("{{client_name}}", clientName)
                .replace("{{appointment_date}}", appointmentDate)
                .replace("{{starting_hour}}", startingHour)
                .replace("{{doctor_name}}", doctorName);
    }


}
