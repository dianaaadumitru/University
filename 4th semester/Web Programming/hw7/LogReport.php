<?php
class LogReport
{
    public $id;
    public $userID;
    public $message;
    public $type;
    public $severity;
    public $posted_on;

    /**
     * LogReport constructor.
     * @param $id
     * @param $userID
     * @param $message
     * @param $type
     * @param $severity
     * @param $posted_on
     */
    public function __construct($id, $userID, $message, $type, $severity, $posted_on)
    {
        $this->id = $id;
        $this->userID = $userID;
        $this->message = $message;
        $this->type = $type;
        $this->severity = $severity;
        $this->posted_on = $posted_on;
    }

    /**
     * @return mixed
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * @param mixed $id
     */
    public function setId($id)
    {
        $this->id = $id;
    }

    /**
     * @return mixed
     */
    public function getUserId()
    {
        return $this->userID;
    }

    /**
     * @param mixed $user_id
     */
    public function setUserId($userID)
    {
        $this->userID = $userID;
    }

    /**
     * @return mixed
     */
    public function getMessage()
    {
        return $this->message;
    }

    /**
     * @param mixed $message
     */
    public function setMessage($message)
    {
        $this->message = $message;
    }

    /**
     * @return mixed
     */
    public function getType()
    {
        return $this->type;
    }

    /**
     * @param mixed $type
     */
    public function setType($type)
    {
        $this->type = $type;
    }

    /**
     * @return mixed
     */
    public function getSeverity()
    {
        return $this->severity;
    }

    /**
     * @param mixed $severity
     */
    public function setSeverity($severity)
    {
        $this->severity = $severity;
    }

    /**
     * @return mixed
     */
    public function getPostedOn()
    {
        return $this->posted_on;
    }

    /**
     * @param mixed $posted_on
     */
    public function setPostedOn($posted_on)
    {
        $this->posted_on = $posted_on;
    }


}