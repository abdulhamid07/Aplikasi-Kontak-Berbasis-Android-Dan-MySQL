<?php  
	
	$id = $_GET['id'];
	//digunakan untuk mengambil nilai id ketika slah satu LIstView dipilih

	require_once 'koneksi.php';
	
	$sql = "SELECT * FROM tb_kontak WHERE id = '$id'";

	$result = array();
	$r = mysqli_query($con,$sql);

	while ($row = mysqli_fetch_array($r)) {
	//digunakan untuk mengambil data dlam bentuk array 
		
		array_push($result, array(
				"id" => $row['id'],
				"nama_kontak" => $row['nama_kontak'],
				"no_telp" => $row['no_telp'],
			));

	}
	
	echo json_encode(array('result' => $result));
	mysqli_close($con);

?>