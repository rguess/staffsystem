function getDom(id) {
		return document.getElementById(id);
	}
	function displayImage(container, dataURL) {
		$(container).html("");
		var img = document.createElement('img');
		img.setAttribute("width","100px");
		img.setAttribute("height","150px");
		img.src = dataURL;
		container.appendChild(img);
	}
	function handleFiles(files) {
		for ( var i = 0; i < files.length; i++) {
			var file = files[i];
			var imageType = /image.*/;

			if (!file.type.match(imageType)) {
				continue;
			}

			var reader = new FileReader();
			reader.onload = function(e) {
				displayImage(getDom('imageview'), e.target.result);
			}
			reader.readAsDataURL(file);
		}
	}