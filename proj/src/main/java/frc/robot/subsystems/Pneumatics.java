// ************************************************************
// Bishop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: Pneumatics.java
// Intent: Forms a subsystem that controls pneumatics compressor.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import frc.robot.*;

public class Pneumatics extends SubsystemBase
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
//    pneumaticsHub.enableCompressorDigital();
    compressor.enableDigital();
  }

  public void compressorOff()
  {
    compressor.disable();
  }
}
