// global variables that hold fetched tasks and categories
var categories = new Array();
var tasks = new Array();

$(document).ready(function() {
    printCategories();
    printTasks();
    setupNewTaskForm();
});

function showTaskEditForm(task) {
    var categoryList = $('#editTaskCategory').clone();
    $(categoryList).find('[value=' + task.category + ']').attr('selected', 'selected');
    var name = $('<input/>').attr('type', 'text').addClass('nameField').val(task.name);

    var update = $('<input/>').attr('type', 'button').attr('id', 'update').val('Update').click(function() {
        var newCategoryName = $(categoryList).val();
        task.name = $(name).val();
        putTask(task, function() {
            removeTaskEditForm(task)
        });

        if (task.category != newCategoryName) {
            moveTask(task, categories[newCategoryName], function() {
                // update local structures
                delete categories[task.category].tasks[task.id];
                task.category = newCategoryName;
                categories[task.category].tasks[task.id] = task;
                // move task to a different category
                $('#' + task.id).remove();
                addTask(task);
            });
        }

    });

    var form = $('<form/>').attr('id', 'updateTask').append(categoryList).append(name).append(update);
    $('#' + task.id + ' .name').replaceWith(form);
}

function printCategories() {
    getCategories(0, 0, function(data) {
        var category;
        for (key in data) {
            category = data[key];
            categories[category.name] = category;
            category.tasks = new Array();
            addCategory(category);
        }
    }, "all");
}

function printTasks() {
    getTasks("unresolved", 0, 0, function(data) {
        var task;
        for (key in data) {
            task = data[key];
            tasks[task.id] = task;
            categories[task.category].tasks[task.id] = task;
            addTask(task);
        }
        initialized();
    }, "unresolved");
}


function addCategory(category) {
    var categoryName = category.name;
    var escapedCategoryName = escape(categoryName);
    var categoryCell = $('<td/>').attr('colspan', '2').addClass('name').text(categoryName);
    var categoryRow = $('<tr/>').attr('id', categoryName).append(categoryCell);
    $('#categories tbody').append(categoryRow).appendTo('#categories');
    $('<option/>').attr('value', categoryName).text(categoryName).appendTo('#editTaskCategory');
}

function addTask(task) {
    var parent = $('[id=' + task.category + ']');
    var nameCell = $('<td/>').append($('<span/>').addClass('name').text(task.name));
    var doneButton = $('<img/>').attr('src', 'img/task-done.png').attr('title', 'Resolve this task').click(function(event) {
        event.preventDefault();
        task.resolved = "true";
        putTask(task, function() {
            var taskList = categories[task.category].tasks;
            delete categories[task.category].tasks[task.id];
            $('#' + task.id).remove();
        });
    });
    var editButton = $('<img/>').attr('src', 'img/task-edit.png').attr('title', 'Edit this task').click(function(event) {
        event.preventDefault();
        if ($('#' + task.id + ' #updateTask').size() == 0) {
            showTaskEditForm(task);
        } else {
            removeTaskEditForm(task);
        }
    });
    var deleteButton = $('<img/>').attr('src', 'img/task-delete.png').attr('title', 'Delete this task').click(function(event) {
        event.preventDefault();
        deleteTask(task, function() {
            var taskList = categories[task.category].tasks;
            delete categories[task.category].tasks[task.id];
            $('#' + task.id).remove();
        });
    });
    var buttonCell = $('<td/>').append(doneButton).append(editButton).append(deleteButton);
    $('<tr/>').attr('id', task.id).append(buttonCell).append(nameCell).insertAfter('[id=' + task.category + ']');
}

function removeTaskEditForm(task) {
    $('#' + task.id + ' form').replaceWith($('<span/>').addClass('name').text(task.name));
}

function setupNewTaskForm() {
    $('#editTaskSubmit').click(function() {
        var name = $('#editTaskName').val();
        var categoryName = $('#editTaskCategory').val()
        postTask(categoryName, name, function(response) {
            var location = response.getResponseHeader('Location');
            // reset form value
            $('#editTaskName').val('');
            getTask(location, function(data) {
                addTask(data);
            });
        });
    });
}
