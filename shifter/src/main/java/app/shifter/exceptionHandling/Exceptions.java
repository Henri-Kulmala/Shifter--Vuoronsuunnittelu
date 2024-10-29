package app.shifter.exceptionHandling;

public class Exceptions {

    public static class ShiftConflictException extends RuntimeException {
        public ShiftConflictException(String message) {
            super(message);
        }
    }
    
    public static class OutOfWorkingHoursException extends RuntimeException {
        public OutOfWorkingHoursException(String message) {
            super(message);
        }
    }
    
    public static class UnauthorizedAccessException extends RuntimeException {
        public UnauthorizedAccessException(String message) {
            super(message);
        }

    }

    public static class NotQualifiedException extends RuntimeException {
        public NotQualifiedException(String message) {
            super(message);
        }
    }
}
