package font;

public class Character {

    private int id;
    private double xTextureCoordinates;
    private double yTextureCoordinates;
    private double xMaxTextureCoordinates;
    private double yMaxTextureCoordinates;
    private double xOffset;
    private double yOffset;
    private double sizeX;
    private double sizeY;
    private double xAdvance;

    protected Character(int id, double xTextureCoordinates, double yTextureCoordinates, double xTexSize, double yTexSize,
                        double xOffset, double yOffset, double sizeX, double sizeY, double xAdvance) {
        this.id = id;
        this.xTextureCoordinates = xTextureCoordinates;
        this.yTextureCoordinates = yTextureCoordinates;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.xMaxTextureCoordinates = xTexSize + xTextureCoordinates;
        this.yMaxTextureCoordinates = yTexSize + yTextureCoordinates;
        this.xAdvance = xAdvance;
    }

    protected int getId() {
        return id;
    }

    protected double getxTextureCoordinates() {
        return xTextureCoordinates;
    }

    protected double getyTextureCoordinates() {
        return yTextureCoordinates;
    }

    protected double getXMaxTextureCoordinates() {
        return xMaxTextureCoordinates;
    }

    protected double getYMaxTextureCoordinates() {
        return yMaxTextureCoordinates;
    }

    protected double getxOffset() {
        return xOffset;
    }

    protected double getyOffset() {
        return yOffset;
    }

    protected double getSizeX() {
        return sizeX;
    }

    protected double getSizeY() {
        return sizeY;
    }

    protected double getxAdvance() {
        return xAdvance;
    }

}
