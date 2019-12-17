package app.models;

public class InHousePart extends AbstractPart {
    private int machineId;

    public InHousePart() {}

    public int getMachineId() {
        return this.machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
