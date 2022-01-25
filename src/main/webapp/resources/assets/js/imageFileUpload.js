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

  		function imageCheck(){
 			 //이전 저장된 이미지, 선택한 이미지가 없는 경우 "선택된 이미지가 없습니다." 메세지 리턴
			 console.log(document.getElementById("prevSaveDiv").childElementCount);
  			 console.log(document.getElementById("selectDiv").childElementCount);

  			 if(document.getElementById("prevSaveDiv").childElementCount == 0 && document.getElementById("selectDiv").childElementCount == 0){
  				 window.alert("선택된 이미지가 없습니다.");
  				 return false;
  			 }
  			 return true;
  		}

  		function setHashTagList(){
  			//신규해시태그
 			 var objTags = {"tags":[]};
  			 const tags =  document.querySelectorAll(".badge");
 			 for (var i = 0; i < tags.length; i++) {
 				 var tag = tags[i].innerText;
 				 tag = tag.substring(0, tag.length - 1);
 				 objTags.tags.push(tag);
 				//console.log("tag text:"+tags[i].innerText);
 			 }
 			 const paramTags = JSON.stringify(objTags);
 			 //console.log("paramTags:"+paramTags);
 			 document.getElementById("hashTagList").value = paramTags;
  		}
  		
  		function updateCheck(bulletinId) {
  			 if (bulletinId == null) return;

  			 const arrRemoveSavedImageFileIds = Array.from(setSearchFileIds);
  			 var obj = {"images":[]};
  			 for(var i = 0 ; i < arrRemoveSavedImageFileIds.length ; i++){
  				obj.images.push(arrRemoveSavedImageFileIds[i]);
  			 }
  			 const param = JSON.stringify(obj);
  			 //console.log("param:"+param);
  			 document.getElementById("removeImageList").value = param;

  			 document.getElementById("uploadForm").action = rootPath + "/bulletinboards/newbulletinboard/"+bulletinId;
  		}

  		function loadSavedImage(jsonParam){
  			//이전 저장된 이미지 미리보기
  			const searchData = jsonParam.imageData;
  			const $prevSaveDiv = document.getElementById("prevSaveDiv");

  			for(var i = 0 ; i < searchData.length ; i++){
  	            const $imgDiv = document.createElement("div");
  	            const $childDiv = document.createElement("div");
  	            const $img = document.createElement("img");
  	            const $btn = document.createElement('button');
  	            const $i = document.createElement('i');

  	            $i.style.color =  'black';
  	            $i.style.fontSize = '25px';
  				$i.setAttribute('class', 'fa fa-times');
  				$i.setAttribute('aria-hidden', 'false');

  				$btn.setAttribute('type', 'button');
  				$btn.id = "btnImageRemove"+i;
  	            $btn.addEventListener( "click", onClickRemoveSavedImage);
  	            $btn.value = searchData[i].image;

  	            $img.src = rootPath + '/resources/images/thumbs/'+searchData[i].image;
  	            $img.style.width = "117.02px";
  	            $img.style.height = "160px";

  	            $imgDiv.id = 'imgDiv'+i;
  	            $imgDiv.style.display = "inline-block";
  	            $imgDiv.style.width = "117.02px";
  	            $imgDiv.style.height = "160px";

  	            $childDiv.appendChild($btn);
  				$btn.appendChild($i);
  	            $imgDiv.appendChild($img);
  	            $imgDiv.appendChild($childDiv);
  	            $prevSaveDiv.appendChild($imgDiv);
  			}
  		}

  		function onClickRemoveSavedImage(){
  			const fileId = this.value;
  			const $btn = this;
  			if(!setSearchFileIds.has(fileId)){
  				setSearchFileIds.add(fileId);
  				//미리보기 제거
  				const $childDiv =  $btn.parentElement;
  				const $imgDiv =  $childDiv.parentElement;
  				$imgDiv.parentElement.removeChild($imgDiv);
  			}
  		}

  			function previewImage(input) {
  	  		    //선택한 이미지 파일 미리보기 생성
  	  		    if(input.files) {
  	  		        const fileArr = Array.from(input.files);
  	  		        const $selectDiv = document.getElementById("selectDiv");

  	  		        fileArr.forEach((file, index) => {
  	  		            const reader = new FileReader();
  	  		            const $imgDiv = document.createElement("div");
  	  		            const $img = document.createElement("img");
  	  		            //const $childDiv = document.createElement("div");
  	  		            //const $btn = document.createElement('button');
  	  		            //const $i = document.createElement('i');

  	  		            //$i.style.color =  'black';
  	  		            //$i.style.fontSize = '25px';
  	  					//$i.setAttribute('class', 'fa fa-times');
  	  					//$i.setAttribute('aria-hidden', 'false');

  	  					//console.log("index:"+index);
  	  					//$btn.value = index;//삭제시 필요한 index 지정
  			            //$btn.addEventListener( "click", onClickRemoveSelectImage);
  	  					//$btn.setAttribute('type', 'button');

  			            $imgDiv.style.display = "inline-block";

  			            //$childDiv.appendChild($btn);
  						//$btn.appendChild($i);
  	  		            $imgDiv.appendChild($img);
  			            //$imgDiv.appendChild($childDiv);
  	  		            reader.onload = e => {
  	  		                $img.src = e.target.result;
  	  		                $imgDiv.style.width = "117.02px";
  	  		                $imgDiv.style.height = "160px";
  	  		                $img.style.width = "117.02px";
  	  		                $img.style.height = "160px";
  	  		            }
  	  		            $selectDiv.appendChild($imgDiv);
  	  		            reader.readAsDataURL(file);
  	  		        })
  	  		    }
  	  		}

  	  		function removePreSelectImage(){
  	  		    const $Div = document.getElementById("selectDiv");
  	  		    //하위 태그 삭제
  	  		    while ( $Div.hasChildNodes())
  	  		    {
  	  		    	$Div.removeChild($Div.firstChild);
  	  		    }
  	  		}

  	  		function onClickRemoveSelectImage(){
  	  			document.getElementById("inputMultipleImage").value = "";
  	  			removePreSelectImage();
  	  		document.getElementById('fileStatusSpan').innerHTML = "";
  	  		}
