<?php 
$server_name="localhost";
$user_name="root";
$pass_server="";
$db_name="activeday_tracker"; 

$conn=new mysqli($server_name,$user_name,$pass_server,$db_name);

$userId =  1;

if($conn){
	
	$sql="SELECT activity.*, user_activity.date ,user_activity.done
    FROM activity
    JOIN user_activity ON activity.id = user_activity.activity_Fk_id
     WHERE user_activity.user_Fk_id = '$userId'";
	 
	$result = $conn->query($sql);
	if($result->num_rows > 0){
		while($row = $result->fetch_assoc()){
			
			 $item["id"] = $row["id"];
			 $item["date"]=$row["date"];
             $item["name"] = $row["name"];
             $item["OptimalT"] = $row["OptimalT"];
             $item["Duration"] = $row["Duration"];
             $item["imag"] = $row["imag"];
             $item["done"] = $row["done"];
			
			
	
			$room[] = $item;
			
			    
			
				
		}
		echo json_encode($room);
	} else {
		
		 $item["id"] = 0;
		 $item["done"] = 0;
        $item["name"] = "NO Data";
		 $item["date"]= "NO Data";
        $item["OptimalT"] = "NO Data";
        $item["Duration"] = "NO Data"; 
        $item["imag"] = "NO Data";

	
		
		$room[] = $item;
					
		echo json_encode($room);
	}
	
	
} else {
	
	echo("Error");
}

?>
