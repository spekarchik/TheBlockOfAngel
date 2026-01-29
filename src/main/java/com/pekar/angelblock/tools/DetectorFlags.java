package com.pekar.angelblock.tools;

class DetectorFlags
{
    private final boolean isShiftingOreFound;
    private final boolean isDiamondOreFound;
    private final boolean isAmethystFound;
    private final boolean isRailsFound;
    private final boolean isSculkVeinFound;

    DetectorFlags(boolean isShiftingOreFound, boolean isDiamondOreFound, boolean isAmethystFound, boolean isRailsFound, boolean isSculkVeinFound)
    {
        this.isShiftingOreFound = isShiftingOreFound;
        this.isDiamondOreFound = isDiamondOreFound;
        this.isAmethystFound = isAmethystFound;
        this.isRailsFound = isRailsFound;
        this.isSculkVeinFound = isSculkVeinFound;
    }

    public boolean isShiftingOreFound()
    {
        return isShiftingOreFound;
    }

    public boolean isDiamondOreFound()
    {
        return isDiamondOreFound;
    }

    public boolean isAmethystFound()
    {
        return isAmethystFound;
    }

    public boolean isSculkVeinFound()
    {
        return isSculkVeinFound;
    }

    public boolean isRailsFound()
    {
        return isRailsFound;
    }
}
