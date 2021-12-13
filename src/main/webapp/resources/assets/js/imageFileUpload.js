		function changeFileStatus(file){
			var totalFileSize = 0;
			var browser=navigator.appName;
			
	    	for(let iFile of file.files){
	    		if(/\.(jpeg|gif|png|jpg)$/i.test(iFile.name) == false){
	    			alert('jpeg, gif, png, jpg 이미지 확장자 파일만 업로드 가능합니다.');
		    		file.value = '';
		    		document.getElementById('fileStatusSpan').innerHTML = '';    			
	    			return;
	    		}
	    		totalFileSize += iFile.size;
	    	}
	    	
	    	if(totalFileSize>50000000){//50MB까지 제한
	    		alert('최대 업로드 용량을 초과했습니다.');
	    		file.value = '';
	    		document.getElementById('fileStatusSpan').innerHTML = '';
	    		return;
	    	}
	    	//소수점 2째 자리까지 나오도록 바이트(Byte) -> 메가바이트(MB) 변환 처리
	        totalFileSize = Math.round(totalFileSize / 1024 / 1024 * 100) / 100 + 'MB';
	        document.getElementById('fileStatusSpan').innerHTML = totalFileSize + ' / ' + file.files.length+'개';
		}
		
		function enterTag(){ 			
  			var tagSpan = document.createElement('span');
  			var x = document.createElement('span');	
  			var xMark = 'x';
  			var result = document.getElementById('tag');
  			var input = document.getElementById('tagId');
  			tagSpan.className='badge';
  			x.setAttribute( 'onclick', 'removeTag()' );
  			x.className='xClass';
  					
  			var content =  document.getElementById('tagId');
  			var string = content.value;
  			var string2 = string.trim();
  			var string3 = string2.replace("," , "");		
  			
  			if(string3 !== ""){
  				tagSpan.append(string3);  
  	  			x.append(xMark);
  	  			tagSpan.append(x);
  	  			result.append(tagSpan);  			
  	  			input.value = null;		
  			}else if(string3 == string){}
 
  		}
  		
  		function removeTag(){
  			var listSpan = document.getElementById("tag");
  			listSpan.removeChild(listSpan.childNodes[0]);
  		}
  		$('input[type="text"]').keydown(function() {
  		  if (event.keyCode === 13) {
  		    event.preventDefault();
  		  };
  		});
  		
  		function previewImage(input) {
  		    const multipleContainer = document.getElementById("multipleContainerSection");
  		    //하위 태그 삭제
  		    while ( multipleContainer.hasChildNodes()) 
  		    { 
  		    	multipleContainer.removeChild(multipleContainer.firstChild); 
  		    }
  		    //이미지 파일 미리보기 생성
  		    if(input.files) {
  		        const fileArr = Array.from(input.files);
  		        const $colDiv = document.createElement("div");
  		        $colDiv.classList.add("column");

  		        fileArr.forEach((file, index) => {
  		            const reader = new FileReader();
  		            const $imgDiv = document.createElement("div");   
  		            const $img = document.createElement("img");
  		            $img.classList.add("image");
  		            $imgDiv.appendChild($img);
  		            reader.onload = e => {
  		                $img.src = e.target.result;              
  		                $imgDiv.style.width = "117.02px";
  		                $imgDiv.style.height = "160px";
  		                $img.style.width = "117.02px";
  		                $img.style.height = "160px";
  		            }
  		            $colDiv.appendChild($imgDiv);	            
  		            reader.readAsDataURL(file);
  		        })
  		        multipleContainer.appendChild($colDiv);
  		    }
  		}