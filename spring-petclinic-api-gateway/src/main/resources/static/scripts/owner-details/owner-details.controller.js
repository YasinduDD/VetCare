"use strict";

angular.module("ownerDetails").controller("OwnerDetailsController", [
  "$http",
  "$state",
  "$stateParams",
  function ($http, $state, $stateParams) {
    var self = this;

    // Get owner details
    $http
      .get("api/gateway/owners/" + $stateParams.ownerId)
      .then(function (resp) {
        self.owner = resp.data;
      });

    // Delete owner details
    self.deleteOwner = function () {
      if (!confirm("Are you sure you want to delete this owner?")) {
        return;
      }

      $http
        .delete("api/gateway/owners/" + $stateParams.ownerId)
        .then(function () {
          alert("Owner deleted successfully");
          $state.go("owners"); // Navigate back to the owner list
        })
        .catch(function (error) {
          console.error("Delete failed:", error);
          alert("Failed");
        });
    };
  },
]);
