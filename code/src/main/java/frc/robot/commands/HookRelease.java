// ************************************************************
// Bishop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: HookRelease.java
// Intent: Command/operation to release the hooks to to the arm.
// ************************************************************

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Hooks;

public class HookRelease extends CommandBase
{
  private Hooks hooksSubsystem;

  // ctor
  public HookRelease(Hooks hooks)
  {
    hooksSubsystem = hooks;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(hooksSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    // TODO
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    return false;
  }
}

/*
cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc
cccccccccccccccccccccccccccccccccc:;,;;:cccccccccccccccccccccc::cccccccccccccccccccccccccccccccccccc
ccccccccccccccccccccccccccccccc::,'....',;::ccccccccccccccc:;,'',::ccccccccccccccccccccccccccccccccc
cccccccccccccccccccccccccccccc:;'.........',;;;;;;;;;;:::;'.......,:cccccccccccccccccccccccccccccccc
cccccccccccccccccccccccccccccc:'....''''...............'....''.....,:ccccccccccccccccccccccccccccccc
ccccccccccccccccccccccccccccc:,......,,,'...'''''''''''............':ccccccccccccccccccccccccccccccc
ccccccccccccccccccccccccccc:;'.......',,,,'',,,,,,,,,,,,,'..........,;::cccccccccccccccccccccccccccc
ccccccccccccccccccccccccc:;'....'....',,,,,,,,,,,,,,,,,,,,,''.'''.....',;:cccccccccccccccccccccccccc
cccccccccccccccccccccccc:,....',,,''',,,,,,,,,,,,,,,,,,,,,,,,,,,,,'''....,:ccccccccccccccccccccccccc
cccccccccccccccccccccc:;'....',,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,'...',;::ccccccccccccccccccccc
ccccccccccccccccccc::,'.....',,,,,,,,,,,,,,,,,,,,,,,,,,'..,:'',,,,,,,''......'',,;;::ccccccccccccccc
ccccccccccccccccc:;,.......',,,,,,,,,,,,,,,,,,,,,,,,,,,'..cd:'.'''...,:lodxxdoolc:,'';:ccccccccccccc
ccccccccccccccc:;'........',,,,,,,,,,,,,,,,,,,,,,,,,,,,,'..,,'....':ok0K00000K0dc;,...':cccccccccccc
cccccccccccccc:,..........',,,,,,,,,,,,,,,,,,,,,,,,,,,,,,''''''..;d0K00000000Kk,.......;cccccccccccc
ccccccccccccc:,...........',,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,..cOK0000000000KOd:,'....;cccccccccccc
cccccccccccc:,.............','''''''',,,,,,,,,,,,,,,,,,,,,,,,'.:OK000000000000000Ol;c;':cccccccccccc
ccccccccccc:;.......................',,,,,,,,,,,,,,,,,,,,,,,,.'o00000K0Okkkkkkkkxo;',';:cccccccccccc
ccccccccccc:'......................',,,,,,,,,,,,,,,,,,,,,,,,,.,kK00K0dc,''''''''....';:ccccccccccccc
ccccccccccc;........................',,,,,,,,,,,,,,,,,,,,,,,'.cOK00x:'.......'..,;;';:cccccccccccccc
ccccccccccc;..........................'',,,,,,,,,,,,,,,,,,,,.'d000d,......',;:;,:c:::ccccccccccccccc
ccccccccccc;............................',,,,,,,,,,,,,,,,,,'.;kKKx;',''.';:c:c::cccccccccccccccccccc
ccccccccccc;'............................',,,,,,,,,,,,,,,,,..:OK0l,dOkl'';:::ccccccccccccccccccccccc
ccccccccccc:'............................',,,,,,,,,,,,,,,,'..;kK0o,d00o'.',,:ccccccccccccccccccccccc
cccccccccccc;.............................',,,,,,,,,,,,,,,'..'lOKOc,lkk:...,:ccccccccccccccccccccccc
cccccccccccc:,.............................'',,,,,,,,,,,,,,'...:dkOdc;:,....;ccccccccccccccccccccccc
ccccccccccccc:,........................''.....'',,,,,,,,,,,,'....,:clcccoo,.;ccccccccccccccccccccccc
cccccccccccccc:;'......................',,'......''',,,,,,,,,,'.......';:;',:ccccccccccccccccccccccc
cccccccccccccccc:,'.....................',,,''.......',,,,,,,,''....';;;;;::cccccccccccccccccccccccc
cccccccccccccccccc:;,....................',,,,,'......,,,,,','.....':ccccccccccccccccccccccccccccccc
cccccccccccccccccccc::,'...................',,,,'.....',,,'......';:cccccccccccccccccccccccccccccccc
ccccccccccccccccccccccc:;,'.................',,,'....',,,'.......,:ccccccccccccccccccccccccccccccccc
cccccccccccccccccccccccccc:;'................','.....'''.........;cccccccccccccccccccccccccccccccccc
cccccccccccccccccccccccccccc::,'..............'.................,:cccccccccccccccccccccccccccccccccc
ccccccccccccccccccccccccccccccc:;,'...........................';:ccccccccccccccccccccccccccccccccccc
cccccccccccccccccccccccccccccccccc:;'......................',;:ccccccccccccccccccccccccccccccccccccc
cccccccccccccccccccccccccccccccccccc:;,'.................';:cccccccccccccccccccccccccccccccccccccccc
ccccccccccccccccccccccccccccccccccccccc:;'............',::cccccccccccccccccccccccccccccccccccccccccc
ccccccccccccccccccccccccccccccccccccccccc::;,''''''',;:ccccccccccccccccccccccccccccccccccccccccccccc
ccccccccccccccccccccccccccccccccccccccccccccc::::::ccccccccccccccccccccccccccccccccccccccccccccccccc
cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc
*/
