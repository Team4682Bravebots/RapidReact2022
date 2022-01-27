// ************************************************************
// Bishop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: Hooks.java
// Intent: Forms model for the Hooks subsystem.
// ************************************************************

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Hooks extends SubsystemBase {

  // ctor
  public Hooks()
  {
  }

  @Override
  public void periodic()
  {
    // This method will be called once per scheduler run
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
