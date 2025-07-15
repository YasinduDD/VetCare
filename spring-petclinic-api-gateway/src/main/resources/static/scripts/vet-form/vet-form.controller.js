"use strict";

angular.module("vetForm").controller("VetFormController", [
  "$http",
  "$state",
  function ($http, $state) {
    var self = this;
    self.vet = { specialties: [] };
    self.specialties = [];

    // Fetch specialties
    $http.get("api/gateway/vets/specialties").then(function (resp) {
      self.specialties = resp.data;
    });

    self.toggleSpecialty = function (specialty) {
      var idx = self.vet.specialties.findIndex(function (s) {
        return s.id === specialty.id;
      });
      if (idx > -1) {
        self.vet.specialties.splice(idx, 1);
      } else {
        self.vet.specialties.push(specialty);
      }
    };

    self.isSpecialtySelected = function (specialty) {
      return self.vet.specialties.some(function (s) {
        return s.id === specialty.id;
      });
    };

    self.submit = function () {
      $http
        .post("api/gateway/vets", self.vet)
        .then(function () {
          $state.go("vets");
        })
        .catch(function (error) {
          alert(
            "Failed to add vet: " +
              (error.data && error.data.message
                ? error.data.message
                : "Unknown error")
          );
        });
    };
  },
]);
