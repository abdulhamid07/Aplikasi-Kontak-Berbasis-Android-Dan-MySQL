<?php  
	
	require_once 'koneksi.php';
	//digunakn untuk menambahkan perintah untuk koneksi ke db
	//yang ada di file koneksi.php
	
	$sql = "SELECT * FROM tb_kontak";

	$result = array();
	$r = mysqli_query($con,$sql);
	//perintah unttuk menjalankan query

	while ($row = mysqli_fetch_array($r)) {
		
		array_push($result, array(
		//memasukan data ke dalam array 
				"id" => $row['id'],
				"nama_kontak" => $row['nama_kontak'],
				"no_telp" => $row['no_telp']
			));

	}
	
	echo json_encode(array('result' => $result));
	//digunakan untuk mengubah data dari array menjai JSON
	mysqli_close($con);
	//untuk menutup perintah sql.

?>