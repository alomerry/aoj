package mo.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class PermissionDeniedException extends Exception {

    /**
     *
     */
    @Setter
    @Getter
    private String message;

    /**
     *
     */
    @Getter
    private String reason;

    /**
     *
     * @param message
     */
    public PermissionDeniedException(String message) {
        super(message);
        this.message = message;
    }
}
