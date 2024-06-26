$(function () {
    summernote();

    communityCategory({ refCategoryNo: 2, categoryLevel: 1 }, (res) => {
        // const navArr = [{ categoryNo: 0, categoryName: "커뮤니티 메인" }, ...res];
        setCategory(res, 'community-division')
        // setNav(navArr);
        // setChecked("division");
    });

    communityCategory({ refCategoryNo: 2, categoryLevel: 2 }, (res) => {
        setCategory(res, 'community-genre')
        setChecked("genre");
    });

    $('input').keydown(function (ev) {
        if (ev.keyCode === 13) {
            ev.preventDefault();
        };
    });

    $("#community-submit button:nth-child(1)").on("click", (ev) => {
        const data = {};
        data.boardNo = $("input[name=boardNo]").val();
        data.userNo = $("input[name='userNo']").val();
        data.boardTitle = $("input[name='boardTitle']").val();
        data.categoryNo = categoryForm();
        data.concertNo = $("input[name='concertNo']").val();
        data.boardContent = $(".note-editing-area>.note-editable").html();

        if(!(data.userNo && data.boardTitle.length > 0 && data.categoryNo && data.boardContent !== '<p><br></p>')) {
            alert("빠진 부분이 없는지 확인 바랍니다.");
            return;
        }

        updateBoard(data, res => {
            location.href = contextPath + res;
        });
    });
});

function setNav(result) {
    const ul = document.getElementById("community-nav");
    for (let ele of result) {
        const li = document.createElement("li");
        const h2 = document.createElement("h2");
        const input = document.createElement("input");

        li.setAttribute("class", "community-nav-li");
        li.setAttribute("onclick", `location.href='main.cm?categoryNo=${ele.categoryNo}'`);

        h2.innerHTML += ele.categoryName;
        input.value = ele.categoryNo;
        input.setAttribute("class", "community-hidden-input");
        input.type = "hidden"

        li.appendChild(h2);
        li.appendChild(input)
        ul.appendChild(li);
    }

    ul.innerHTML += `<li id="write-btn" onclick="location.href='incertForm.cm'">
                        <h2>글쓰기</h2>
                        <img src="${contextPath}/resources/img/community/communityMain/write.png">
                    </li>`;
}

function setChecked(name) {
    const check = document.querySelector(`input[name=${name}]`).value;
    const categoryBtn = document.querySelectorAll(`#community-${name}>.btn-staez`);
    const categoryRadio = document.querySelectorAll(`#community-${name} input[name='categoryNo']`);
    for (let i in categoryBtn) {
        try {
            if (categoryBtn[i].innerText === check) {
                categoryBtn[i].classList.add("checked");
                categoryRadio[i].setAttribute("checked", true);
                break;
            }
        } catch (error) {
            continue;
        }
    }
}

function setColor() {
    const communityNav = document.getElementsByClassName("community-nav-li");
    for (let i = 0; i < communityNav.length; i++) {

    }
}

function generateUrls() {
    const input = document.getElementById('categoryInput').value;
    const categoryNos = input.split(',').map(num => num.trim());
    const outputDiv = document.getElementById('output');

    let urls = [];

    // Single categoryNo
    categoryNos.forEach(categoryNo => {
        urls.push(`/staez/main.com?categoryNo=${categoryNo}`);
    });

    // Multiple categoryNos
    if (categoryNos.length > 1) {
        let multipleCategoryNo = `/staez/main.com?categoryNo=${categoryNos.join('&categoryNo=')}`;
        urls.push(multipleCategoryNo);
    }

    // Display the URLs
    outputDiv.innerHTML = '<h2>Generated URLs:</h2>';
    urls.forEach(url => {
        const urlElement = document.createElement('p');
        urlElement.textContent = url;
        outputDiv.appendChild(urlElement);
    });
}

// 썸머노트에 이미지업로드가 발생하였을 때 동작하는 함수
function fileUpload(fileList) {
    // 썸머노트는 이미지를 추가하면 해당이미지파일을 전달해준다.
    // callback함수를 작성하지 않을경우 자동으로 이미지를 string으로 변환해서 보여주지만
    // customCallback을 작성할 경우 해당 이미지의 경로를 직접 주어야한다.

    // 파일업로드를 할때는 form태그에서 encType을 multipart/form-data형식으로
    // 요청하는 것처럼 자바스크립트 객체에 FormData객체를 이용해서 ajax요청을 해준다.
    const fd = new FormData();
    for (let file of fileList) {
        fd.append("fileList", file);
    }

    insertFileApi(fd, function (nameList) {
        for (let name of nameList) {
            $("#summernote").summernote('insertImage', contextPath + name);
        }
    });
}

function searchResult(_this) {
    const keyword = _this.value;
    searchConcertList({ "keyword": keyword }, res => drawResultList(res));
}

function drawResultList(result) {
    const ul = document.querySelector("#community-tag .search-list");
    ul.innerHTML = "";

    for (let ele of result) {
        const li = document.createElement("li");
        const btn = document.createElement("button");
        const img = document.createElement("img");
        const input = document.createElement("input");
        const span = document.createElement("span");
        const poster = document.createElement("img");

        btn.setAttribute("type", "button");
        img.setAttribute("src", contextPath + "/resources/img/community/communityMain/search-icon.png");
        input.setAttribute("type", "hidden");
        path = ele.originName === 'api' ? ele.filePath : contextPath + ele.filePath + ele.changeName;
        poster.setAttribute("src", path);
        poster.setAttribute("class", "poster");
        input.value = ele.concertNo;
        span.innerHTML = ele.concertTitle;

        btn.appendChild(img);
        li.appendChild(btn);
        li.appendChild(input);
        li.appendChild(span);
        li.appendChild(poster);
        ul.appendChild(li);

        li.addEventListener("click", (ev) => {
            const theaterName = document.querySelector("#community-tag input[name='concertTitle']")
            const theaterNo = document.querySelector("#community-tag input[name='concertNo']");
            theaterNo.value = ev.currentTarget.children[1].value;
            theaterName.value = ev.currentTarget.children[2].innerHTML;
            ul.innerHTML = "";
        });
    }
}

function categoryForm() {
    const category = document.querySelectorAll("input[name='categoryNo']");
    let arr = [];

    for (let ele of category) {
        if (ele.getAttribute("checked")) {
            arr.push(ele.value);
        }
    }

    return arr;
}