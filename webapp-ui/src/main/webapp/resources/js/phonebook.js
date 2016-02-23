function readImage() {
    if (this.files && this.files[0]) {
        var FR = new FileReader();
        FR.onload = function (e) {
            $('#photo').attr("src", e.target.result);
        };
        FR.readAsDataURL(this.files[0]);
    }
}