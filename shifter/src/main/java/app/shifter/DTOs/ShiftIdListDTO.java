
package app.shifter.DTOs;

import java.util.List;

public class ShiftIdListDTO {
    private List<Long> shiftIds;

   
    public List<Long> getShiftIds() {
        return shiftIds;
    }

    public void setShiftIds(List<Long> shiftIds) {
        this.shiftIds = shiftIds;
    }
}