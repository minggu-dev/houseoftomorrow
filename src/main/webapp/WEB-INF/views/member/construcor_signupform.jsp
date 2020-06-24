<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원가입 폼</title>
    <script src="/plugins/jquery/jquery-3.4.1.min.js"></script>
    
    <link rel="stylesheet" href="/plugins/bootstrap/bootstrap.min.css">
	<script src="/plugins/bootstrap/bootstrap.min.js"></script>

	<script	src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script src="/js/addr/addr.js"></script>
   
<script>
function myFunction(){
	var obj = document.getElementById('mailselect');
	var obj2 = document.getElementById('aaa');
	if(obj.value==='person'){
		obj2.style.display = 'inline-block';
	} else {
		obj.style.display = 'inline-block';
		obj2.style.display = 'none';
	}	
}   

$(function(){
	$('input[name=isCompany]').on('change', function(){
		console.log($(this).val());
		if($(this).val()==1){
			$('label[for=conCertification]').text('사업자 등록번호');
			console.log('t');
		}else{
			$('label[for=conCertification]').text('주민등록번호');
			console.log('f');
		}
	});
	
});
</script>
   
</head>

<body>
	<div class="container">
		<div class="row">
		 <form class="row" enctype="multipart/form-data" action="/signup" method="POST">
				<div class="form-group col-md-12">
				<h1>회원 정보</h1>
				</div>
				<div class="form-group col-md-12">
                    <label for="memberId">이메일 (email)</label>
                    <p>8자 이상 입력해주세요.</p>
                    <div class="input-group">
                        <div class="input-group-prepend">
                        	<input type="text" class="form-control"> @
                        	
                        	<input type="text" id="aaa" class="form-control" style="display: none;">
                        	<select id="mailselect" onchange="myFunction()" style="display: inline-block;">
                        		<option>선택</option>
                        		<option>naver.com</option>
                        		<option>hanmail.com</option>
                        		<option>nate.com</option>
                        		<option>gmail.com</option>
                        		<option value="person">직접입력</option>
                        	</select>
                        	<!-- <span class="input-group-text"></span>
                            <span class="input-group-text">@</span> -->
                        </div>
                    <!--     <input type="text" class="form-control" id="username" placeholder="Username" required name="user-id"> -->
                        <div class="invalid-feedback" style="width: 100%;">Your username is required.</div>
                    </div>
                
                </div>
                <div class="form-group col-md-6">
                    <label for="memberPwd">비밀번호</label> 
                    <input type="password" class="form-control" id="memberPwd" placeholder="비밀번호" value="" required name="memberPwd">
                    <div class="invalid-feedback">유효한 비밀번호가 필요합니다.</div>
                </div>


                <div class="form-group col-md-6">
                    <label for="memberPwdChk">비밀번호 확인</label> 
                    <input type="password" class="form-control" id="memberPwdChk" placeholder="비밀번호 확인" value="" required name="memberPwdChk">
                    <div class="invalid-feedback">유효한 비밀번호가 필요합니다.</div>
                </div>
                
                <div class="col-md-6">
					<label for="memberPhone">회원 연락처</label> 
                    <input type="text" class="form-control" id="memberPhone" placeholder="전화번호" name="memberPhone" required>
				</div>
           

                <div class="form-group col-md-6">
                    <label for="memberName">이름</label> 
                    <input type="text" class="form-control" id="memberName" placeholder="별명(2자-15자)" required name="memberName">
                    <div class="invalid-feedback">이름을 입력하세요.</div>
                </div>			
				<div class="form-group col-md-12">
				<h1>시공사 정보 입력</h1>
				</div>
				<div class="form-group col-md-6">
					<label for="conName">시공사명</label> 
                    <input type="text" class="form-control" id="conName" placeholder="시공사명" name="conName" required>
				</div>
				
				<div class="form-group col-md-6">
					<label for="conPhone">시공사 연락처</label> 
                    <input type="text" class="form-control" id="conPhone" placeholder="시공사 전화번호" name="conPhone" required>
				</div>
				
				<div class="form-group col-md-6">
					<label for="conCareer">경력사항</label> 
                    <input type="text" class="form-control" id="conCareer" placeholder="기간을 입력해주세요" name="conCareer" required>
				</div>
				
				<div class="form-group col-md-6">
					<label for='company'>사업자 여부</label><br>
					<input type="radio" id="company" name="isCompany" value="1" checked>
					<label for="company">사업자</label>
					<input type="radio" id="freelancer" name="isCompany" value="0">
					<label for="freelancer">프리랜서</label>
				</div>
				
				<div class="form-group col-md-6">
					<label for="conCertification">사업자 등록번호</label> 
                    <input type="text" class="form-control" id="conCertification" placeholder="기간을 입력해주세요" name="conCertification" required>
				</div>
				
					
					<div id='addr' class="form-group col-md-6">
						<div class='row'>
							<label class="col-md-4" for="postalcode">주소</label>
							<div class="w-100"></div>
							<input type="text" class="form-control col-md-5" id="postcode" name="postalCode" placeholder="우편번호" readonly>
							&nbsp;&nbsp;
							<button class="btn btn-info signBtn col-md-3" id="userAddrBtn" type="button">우편번호 찾기</button>
							<input type="text" class="form-control col-md-12" id="roadAddress" name="reaodAddress" placeholder="도로명주소" readonly>
							<input class="form-control col-md-4" id="extraAddress" name="extraAddress" type="text" readonly>
							<input class="form-control col-md-12" id="detailAddress" name="detailAddress" placeholder="상세주소" type="text">
						</div>
					</div>
				</form>
		</div>
	</div>




    
				
				<!-- div class="mb-3">
					<label for="agree">약관동의</label>
					
					<table class="table table-border">
						<td>
							 <hr>	
							 <input type="checkbox" id="" value="option1"> 전체동의 <br>
							 <hr>	
							 <input type="checkbox" id="" value="option2"> 만 14세 이상입니다.(필수)) <br>
							 <input type="checkbox" id="" value="option3"> 이용약관(필수) <br>
							 <input type="checkbox" id="" value="option4"> 개인정보처리방침(필수) <br>
							 <input type="checkbox" id="" value="option5"> 이벤트, 프로모션 알림 메일 및 SMS 수신(선택) <br>
						</td>
					
					</table>
					
				</div>
				
				
				
                <hr class="mb-4">
                <button class="btn btn-primary btn-lg btn-block" type="submit">회원가입 완료</button>
                <hr class="mb-4">

                <footer th:replace="/fragments/semantic :: footer"></footer> -->
            </form>
            </div>
        </div>

    </div>

</body>

</html>