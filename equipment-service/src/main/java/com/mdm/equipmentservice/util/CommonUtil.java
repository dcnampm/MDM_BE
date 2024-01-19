package com.mdm.equipmentservice.util;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.text.NumberFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class CommonUtil {
    /**
     * Check if both date is not null and from date must be after the to date
     *
     * @param from
     * @param to
     *
     * @return true if valid, false if invalid
     */
    public static boolean validateLocalDateTimeBetween(LocalDateTime from, LocalDateTime to) {
        if (validateBetweenNotAnyNull(from, to))
            return false;
        return !from.isAfter(to);
    }

    public static boolean validateBetweenNotAnyNull(Object from, Object to) {
        if (from == null || to == null) {
            return true;
        }
        return false;
    }

    /**
     * Check if both date is not null and from date must be after the to date
     *
     * @param from
     * @param to
     *
     * @return true if valid, false if invalid
     */
    public static boolean validateYearBetween(Year from, Year to) {
        if (validateBetweenNotAnyNull(from, to))
            return false;
        return !from.isAfter(to);
    }

    public static boolean validateIntegerBetween(Double from, Double to) {
        if (validateBetweenNotAnyNull(from, to)) {
            return false;
        }
        return from < to;
    }

    public static String upperCaseFirstLetter(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    public static String getFileName(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        assert originalFileName != null;
        return originalFileName.substring(0, originalFileName.lastIndexOf('.'));
    }

    public static String getFileExtension(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        assert originalFileName != null;
        return originalFileName.substring(originalFileName.lastIndexOf('.') + 1);
    }

    public static MediaType getMediaTypeFromCorrespondingExtension(String fileExtension) {
        return switch (fileExtension) {
            case "png" -> MediaType.IMAGE_PNG;
            case "txt" -> MediaType.TEXT_PLAIN;
            case "jpg", "jpeg" -> MediaType.IMAGE_JPEG;
            case "gif" -> MediaType.IMAGE_GIF;
            case "pdf" -> MediaType.APPLICATION_PDF;
            default -> MediaType.APPLICATION_OCTET_STREAM;
        };
    }

    /**
     * Generate random string of digit with given length
     *
     * @param length
     *
     * @return
     */
    public static String generateRandomStringOfDigit(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int random = (int) (Math.random() * 10);
            stringBuilder.append(random);
        }
        return stringBuilder.toString();
    }

    /**
     * Generate ticket code, you can also overload this function to customize the generated code
     */
    public static String generateTicketCode() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static String convertToCurrency(String digits) {
        if (digits == null || digits.isEmpty())
            return "";
        try {
            // Parse the input string as a double
            double amount = Double.parseDouble(digits);

            // Create a NumberFormat instance for the default locale
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

            // Format the amount as currency
            return currencyFormatter.format(amount);
        } catch (NumberFormatException e) {
            // Handle the case when the input string cannot be parsed as a double
            return digits; // Return the original string if it is not a valid number
        }
    }


    public static LocalDateTime createLocalDateTimeFromTimestamp(Long timestampSeconds) {
        if (timestampSeconds == null) {
            return null;
        }
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestampSeconds), TimeZone.getDefault().toZoneId());
    }
    public static <T> List<T> addNewElementToList(List<T> list, T element) {
        list.add(element);
        return list;
    }
}
