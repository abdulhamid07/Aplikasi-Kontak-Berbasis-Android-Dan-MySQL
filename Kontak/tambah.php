<?php  

	if ($_SERVER['REQUEST_METHOD'] == 'POST') {
	//jika tipe method yang dikirmkan dari form aplikasi sma dengan POST
		$name = $_POST['nama_kontak'];
		$telp = $_POST['no_telp'];
		//simpan data yang di kirim dari form ke masing-maisng variabel

		$sql = "INSERT INTO tb_kontak (nama_kontak,no_telp) VALUES ('$name','$telp')";

		require_once('koneksi.php');

		if (mysqli_query($con,$sql)) {
			echo "Berhasil ditambah";
			//jika tidak terdapat keslahan dlm query memasukan data maka akan menmapilkan pesan berhasil di tambah
		}else{
			echo mysqli_error();
		}

		mysqli_close($con);
	}
?>