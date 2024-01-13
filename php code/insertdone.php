<?php 

$server_name="localhost";
$user_name="root";
$pass_server="";
$db_name="activeday_tracker"; 

$conn=new mysqli($server_name,$user_name,$pass_server,$db_name);


  $userId = $_POST['uid'];
  $acId = $_POST['aid'];
///$n=1;
if($conn){
	
	
			 
			$sql2 = "UPDATE user_activity SET done =1 WHERE user_Fk_id = '$userId' AND activity_Fk_id = '$acId' ";
			
			if($conn->query($sql2) == TRUE){
				$info["error"] = false;
				echo json_encode($info);
				
			}else{
				
				$info["error"] = true;
				echo json_encode($info);
				
			}
			
			$conn->close();
		}
		
	

 else{
	 echo("Error");
 }



?>