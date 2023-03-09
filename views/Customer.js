    function findAllCustomer(){
        $.ajax({
            headers: {
                Authorization: "Bearer " + sessionStorage.getItem("token"),
            },
            type: "GET",
            url: "http://localhost:8080/customers",
            success(data){
                displayCustomer(data)
                $("#customerForm").hide()
                $("#customer").show()
            }
        })
    }

    function findAllCustomerPage(page){
        $.ajax({
            headers: {
                Authorization: "Bearer " + sessionStorage.getItem("token"),
            },
            type: "GET",
            url: "http://localhost:8080/customers/page?page=" + page + "&size=2",
            success(data){

                displayCustomer(data.content)
                console.log(data.content)
                displayPage(data)
                if (data.pageable.pageNumber === 0) {
                    document.getElementById("backup").hidden = true
                }
                //điều kiện bỏ nút next
                if (data.pageable.pageNumber + 1 === data.totalPages) {
                    document.getElementById("next").hidden = true
                }
                $("#customerForm").hide()
                $("#customer").show()
            }
        })
    }
    function displayCustomer(data){
        let context = `<div class="container">
                                                    <h2 style="text-align: center">List Customer</h2>
                                                    <table class="table table-striped-columns">
                                                    <thead>
                                                        <tr>
                                                          <th>STT</th>
                                                          <th>Image</th>
                                                          <th>Name</th>
                                                          <th>Age</th>
                                                          <th>Address</th>
                                                          <th colspan="2" style="text-align: center">Action</th>
                                                        </tr>
                                                    </thead>
                                                   <tbody>`
        for (let i = 0; i < data.length; i++){
            context+= `<tr>
                                                   <td>${i+1}</td>
                                                   <td><img src="${data[i].imagePath}" style="width: 100px; height: 100px" alt="Picture"></td>
                                                   <td>${data[i].name}</td>
                                                   <td>${data[i].age}</td>
                                                   <td>${data[i].address}</td>
                                                   <td><button class="btn btn-warning" onclick="updateFormCustomer(${data[i].id})">Update</button></td>
                                                   <td><button class="btn btn-danger" onclick="deleteCustomer(${data[i].id})">Delete</button></td>
                                               </tr>`
        }
        context+= `</tbody> </table> </div>`
        document.getElementById("customer").innerHTML = context
    }
    function displayPage(data){
        let content = `<button class="btn btn-primary" id="backup" onclick="isPrevious(${data.pageable.pageNumber})">Previous</button>
    <span>${data.pageable.pageNumber+1} | ${data.totalPages}</span>
    <button class="btn btn-primary" id="next" onclick="isNext(${data.pageable.pageNumber})">Next</button>`
        document.getElementById('page').innerHTML = content;
    }
    //hàm lùi page
    function isPrevious(pageNumber) {
        findAllCustomerPage(pageNumber-1)
    }

    //hàm tiến page
    function isNext(pageNumber) {
        findAllCustomerPage(pageNumber+1)
    }
    function backToCustomer() {
        $("#customerForm").hide()
        $("#customer").show()
        event.preventDefault()
    }

    function createCustomerForm(){
        document.getElementById("customerForm").reset()
        document.getElementById("title").innerHTML = "CREATE"
        document.getElementById("action").setAttribute("onclick", "createCustomer()")
        document.getElementById("action").innerHTML = "Create"
        $("#customerForm").show()
        $("#customer").hide()
    }
   function createCustomer() {
       let customer = {
           name : $("#name").val(),
           age : $("#age").val(),
           address : $("#address").val(),
           imagePath : ""
       }
       let formData = new FormData();
       formData.append("file", $('#file')[0].files[0])
       formData.append("customer",
           new Blob([JSON.stringify(customer)], {type: 'application/json'}))
       console.log(customer)
       $.ajax({
           headers: {
               Authorization: "Bearer " + sessionStorage.getItem("token"),
           },
           // headers: {
           //     'Accept': 'application/json',
           //     'Content-Type': 'application/json'
           // },
           contentType: false,
           processData: false,
           url: "http://localhost:8080/customers/save",
           type: "POST",
           data: formData,
           success() {
               alert("Success!")
               findAllCustomerPage(0)
           }
       })
       event.preventDefault()
   }

   function updateFormCustomer(id) {
       $.ajax({
           headers: {
               Authorization: "Bearer " + sessionStorage.getItem("token"),
           },
           url : `http://localhost:8080/customers/${id}`,
           type: "GET",
           success(data) {
               $("#name").val(data.name)
               $("#age").val(data.age)
               $("#address").val(data.address)
               document.getElementById("title").innerHTML = "UPDATE"
               document.getElementById("action").setAttribute("onclick", `updateCustomer(${id})`)
               document.getElementById("action").innerHTML = "Update"
               $("#customer").hide()
               $("#customerForm").show()
           }
       })
   }
    function updateCustomer(id) {
        let customer = {
            id: id,
            name : $("#name").val(),
            age : $("#age").val(),
            address : $("#address").val(),
            imagePath : ""
        }
        let formData = new FormData();
        formData.append("file", $('#file')[0].files[0])
        formData.append("customer",
            new Blob([JSON.stringify(customer)], {type: 'application/json'}))
        console.log(customer)
        $.ajax({
            headers: {
                Authorization: "Bearer " + sessionStorage.getItem("token"),
            },
            // headers: {
            //     // 'Accept': 'application/json',
            //     // 'Content-Type': 'application/json'
            // },
            contentType: false,
            processData: false,
            url: "http://localhost:8080/customers/save",
            type: "POST",
            data: formData,
            success() {
                alert("Success!")
                findAllCustomerPage(0)
            }
        })
        event.preventDefault()
    }
    function deleteCustomer(id){
        if (confirm("Do you want to delete ?")){
            $.ajax({
                headers: {
                    // 'Accept': 'application/json',
                    // 'Content-Type': 'application/json',
                    Authorization: "Bearer " + sessionStorage.getItem("token"),
                },
                url: `http://localhost:8080/customers/delete/${id}`,
                type: "DELETE",

                success() {
                    alert("Delete successfully!")
                    findAllCustomer()
                }
            })
        }
        event.preventDefault()
    }
    function searchByName(page) {
        let search = $("#search").val()
        $.ajax({
            headers: {
                Authorization: "Bearer " + sessionStorage.getItem("token"),
            },
            type: "GET",
            url: "http://localhost:8080/customers/search?search=" + search+"&page="+ page + "&size=2",
            success: function (data) {
                displayCustomerByName(data.content)
                displayPageSearch(data)
                if (data.pageable.pageNumber === 0) {
                    document.getElementById("backup").hidden = true
                }
                //điều kiện bỏ nút next
                if (data.pageable.pageNumber + 1 === data.totalPages) {
                    document.getElementById("next").hidden = true
                }
                $("#customerForm").hide()
                $("#customer").hide()
            }
        })
    }
    function displayCustomerByName(data){
        let context = `<div class="container">
                                                    <h2 style="text-align: center">List Customer</h2>
                                                    <table class="table table-striped-columns">
                                                    <thead>
                                                        <tr>
                                                          <th>STT</th>
                                                          <th>Image</th>
                                                          <th>Name</th>
                                                          <th>Age</th>
                                                          <th>Address</th>
                                                          <th colspan="2" style="text-align: center">Action</th>
                                                        </tr>
                                                    </thead>
                                                   <tbody>`
        for (let i = 0; i < data.length; i++){
            context+= `<tr>
                                                   <td>${i+1}</td>
                                                   <td><img src="${data[i].imagePath}" style="width: 100px; height: 100px" alt="Picture"></td>
                                                   <td>${data[i].name}</td>
                                                   <td>${data[i].age}</td>
                                                   <td>${data[i].address}</td>
                                                   <td><button class="btn btn-warning" onclick="updateFormCustomer(${data[i].id})">Update</button></td>
                                                   <td><button class="btn btn-danger" onclick="deleteCustomer(${data[i].id})">Delete</button></td>
                                               </tr>`
        }
        context+= `</tbody> </table> </div>`
        document.getElementById("searchByName").innerHTML = context
    }
    function displayPageSearch(data){
        let content = `<button class="btn btn-primary" id="backup" onclick="isPreviousByName(${data.pageable.pageNumber})">Previous</button>
    <span>${data.pageable.pageNumber+1} | ${data.totalPages}</span>
    <button class="btn btn-primary" id="next" onclick="isNextByName(${data.pageable.pageNumber})">Next</button>`
        document.getElementById('pageSearch').innerHTML = content;
    }
    function isPreviousByName(pageNumber) {
        searchByName(pageNumber-1)
    }

    function isNextByName(pageNumber) {
        searchByName(pageNumber+1)
    }
