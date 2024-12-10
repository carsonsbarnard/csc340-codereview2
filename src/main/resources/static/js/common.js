document.addEventListener('DOMContentLoaded', function () {
    function confirmDeletion(element) {
        var deleteUrl = element.getAttribute('data-href');
        var confirmed = confirm("Do you want to delete this item?");

        if (confirmed) {
            window.location.href = deleteUrl;
        }

        return false;
    }


    window.confirmDeletion = confirmDeletion;
});
