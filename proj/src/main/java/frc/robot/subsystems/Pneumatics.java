// ************************************************************
// Bishop Blanchet Robotics
// Home of the Cybears
// FRC - Rapid React - 2022
// File: Pneumatics.java
// Intent: Forms a subsystem that controls pneumatics compressor.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import frc.robot.*;

public class Pneumatics extends SubsystemBase implements Sendable
{
//  private final PneumaticsControlModule pneumaticsHub = 
//    new PneumaticsControlModule(Constants.robotPneumaticsControlModuleCanId);
  private final Compressor compressor = new Compressor(
    Constants.robotPneumaticsControlModuleCanId,
    Constants.robotPneumaticsControlModuleType);

  public Pneumatics()
  {
    CommandScheduler.getInstance().registerSubsystem(this);
  }

  @Override
  public void setDefaultCommand(Command myCommand)
  {
      // TODO Auto-generated method stub
      super.setDefaultCommand(myCommand);
  }

  public void compressorOn()
  {
    compressor.enableDigital();
  }

  public void compressorOff()
  {
    compressor.disable();
  }

  @Override
  public void initSendable(SendableBuilder builder)
  {
    builder.addDoubleProperty("CompressorTankPressure", this::getTankPressure, null);
    builder.addBooleanProperty("CompressorIsCompressorEnergized", this::isCompressorEnergized, null);
    builder.addBooleanProperty("CompressorIsPressureSwitchEnabled", this::IsPressureSwitchEnabled, null);
  }

  private double getTankPressure()
  {
    return compressor.getPressure();
  }

  private boolean isCompressorEnergized()
  {
    return (compressor.getCurrent() > 0.01);
  }
  
  private boolean IsPressureSwitchEnabled()
  {
    return compressor.getPressureSwitchValue();
  }

}
