// global variables that hold fetched tasks and categories
var categories = new Array();

$(document).ready(function() {
    printCategories();
    prepareNewCategoryForm();
});

function addCategory(categoryName) {
    var nameCell = $('<td/>').addClass('name').text(categoryName);
    var deleteButton = $('<img/>').attr('src', 'img/task-delete.png').attr('title', 'Delete this category').click(function(event) {
        event.preventDefault();
        deleteCategory(categoryName, function() {
            // remove the category from global variable
            delete categories[categoryName];
            // remove the category from the page
            $('[id=' + categoryName + ']').remove();
        });
    });
    var buttonCell = $('<td/>').append(deleteButton);
    $('<tr/>').attr('id', categoryName).append(buttonCell).append(nameCell).appendTo('#categories tbody');
}

/*
 * Obtain a list of Categories and render it on the page
 */
function printCategories() {
    getCategories(0, 0, function(data) {
        for (key in data) {
            var category = data[key];
            categories[category.name] = category;
            addCategory(category.name);
        }
        initialized();
    }, "all");
}

/*
 * Bind logic to the "Create" button
 */
function prepareNewCategoryForm() {
    $('#editCategorySubmit').click(function(event) {
        event.preventDefault();
        var categoryName = $('#editCategoryName').attr('value');
        putCategory(categoryName, function() {
            addCategory(categoryName);
            $('#editCategoryName').attr('value', '');
        });
    });
}
