<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

	<div class="layer">
		<div class="layer_inner">
			<div class="content">
				<table>
					<tr>
						<td><h3>
								<span class="intro_span">회원가입에 성공하셨습니다!</span>
							</h3></td>
					</tr>
					<tr>
						<td><a class="Button-Move-LOGIN"
							href="<c:url value="/move/login" />">로그인 하러 가기</a></td>
					</tr>
				</table>
			</div>
		</div>
	</div>