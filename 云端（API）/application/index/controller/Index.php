<?php
namespace app\index\controller;

use app\index\model\DevData;

class Index
{
    public function index()
    {

    }


    public function getVal()
    {
        header("Access-Control-Allow-Origin: *");
        $data = new DevData();
        $count = $data->max('id');
        $data = $data->where('id','>',$count-50)->limit(50)->select()->toJson();
        return $data;
    }
}
