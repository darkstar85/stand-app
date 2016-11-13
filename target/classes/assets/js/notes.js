var app = angular.module('notesApp', []);

app.controller('NotesFormCtrl', function ($scope, $http) {
    $scope.note = {};
    $scope.addNote = function () {
        $http({
            method: 'POST',
            url: '/notes/addnote',
            data: $scope.note
        })
            .success(function () {
                location.reload();
            });
    }
});

app.controller('NotesCtrl', function ($scope, $http) {
    $scope.getNotes = function () {
        $http.get('/notes/list')
            .success(function (data) {
                $scope.notes = data;
            });
    };

    $scope.delete = function (id) {
        $http({
            method: 'DELETE',
            url: '/notes/removeNote/' + id
        })
            .success(function () {
                $scope.getNotes();
            });
    }
});