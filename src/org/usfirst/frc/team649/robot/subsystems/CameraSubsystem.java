package org.usfirst.frc.team649.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.vision.AxisCamera;

public class CameraSubsystem extends Subsystem{
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public AxisCamera cam;

    public CameraSubsystem() {
        cam = new AxisCamera("10.6.49.55");
    }

    public ColorImage getImage(){
        try {
			return cam.getImage();
		} catch (NIVisionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }

    protected void initDefaultCommand() {
    }
}
